import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { CABLE_CABLE_TYPE_OPTIONS, CABLE_CRITICALITY_OPTIONS, CableCableType, CableCriticality } from './types';
import { validateCable, cleanCableValues } from './schema';
import { CABLE_DEFAULTS } from './types';
import type { CableFormValues, CableInput } from './types';

interface Props {
  initial?: CableFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: CableInput) => void;
  onCancel?: () => void;
}

export function CableForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<CableFormValues>({ ...CABLE_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof CableFormValues>(key: K, value: CableFormValues[K]) {
    setValues((prev) => ({ ...prev, [key]: value }));
    if (errors[key as string]) {
      setErrors((prev) => {
        const next = { ...prev };
        delete next[key as string];
        return next;
      });
    }
  }

  function submit(ev: React.FormEvent) {
    ev.preventDefault();
    const result = validateCable(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanCableValues(values as Record<string, unknown>) as unknown as CableInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Cable form">
      {serverError ? <div role="alert" className="form-error">{serverError}</div> : null}
      <FormField label="Code" htmlFor="code" error={errors.code} required={true}>
        <Input
          id="code"
          value={values.code ?? ''}
          maxLength={255}
          onChange={(v) => set('code', v)}
        />
      </FormField>
      <FormField label="Name" htmlFor="name" error={errors.name} required={true}>
        <Input
          id="name"
          value={values.name ?? ''}
          maxLength={255}
          onChange={(v) => set('name', v)}
        />
      </FormField>
      <FormField label="Description" htmlFor="description" error={errors.description} required={false}>
        <TextArea
          id="description"
          value={values.description ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('description', v)}
        />
      </FormField>
      <FormField label="Status" htmlFor="status" error={errors.status} required={true}>
        <Select
          id="status"
          value={values.status ?? ''}
          options={LIFECYCLE_OPTIONS}
          allowEmpty={false}
          onChange={(v) => set('status', (v || undefined) as LifecycleStatus | undefined)}
        />
      </FormField>
      <FormField label="External Ref" htmlFor="externalRef" error={errors.externalRef} required={false}>
        <Input
          id="externalRef"
          value={values.externalRef ?? ''}
          maxLength={255}
          onChange={(v) => set('externalRef', v)}
        />
      </FormField>
      <FormField label="Cable Type" htmlFor="cableType" error={errors.cableType} required={false}>
        <Select
          id="cableType"
          value={values.cableType ?? ''}
          options={CABLE_CABLE_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('cableType', (v || undefined) as CableCableType | undefined)}
        />
      </FormField>
      <FormField label="Length M" htmlFor="lengthM" error={errors.lengthM} required={false}>
        <NumberInput
          id="lengthM"
          value={values.lengthM}
          step={0.0001}
          onChange={(v) => set('lengthM', v)}
        />
      </FormField>
      <FormField label="A End Port Id" htmlFor="aEndPortId" error={errors.aEndPortId} required={false}>
        <Input
          id="aEndPortId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.aEndPortId ?? ''}
          onChange={(v) => set('aEndPortId', v)}
        />
      </FormField>
      <FormField label="Z End Port Id" htmlFor="zEndPortId" error={errors.zEndPortId} required={false}>
        <Input
          id="zEndPortId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.zEndPortId ?? ''}
          onChange={(v) => set('zEndPortId', v)}
        />
      </FormField>
      <FormField label="Color" htmlFor="color" error={errors.color} required={false}>
        <Input
          id="color"
          value={values.color ?? ''}
          maxLength={255}
          onChange={(v) => set('color', v)}
        />
      </FormField>
      <FormField label="Label" htmlFor="label" error={errors.label} required={false}>
        <Input
          id="label"
          value={values.label ?? ''}
          maxLength={255}
          onChange={(v) => set('label', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={CABLE_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as CableCriticality | undefined)}
        />
      </FormField>
      <FormField label="Cost Center" htmlFor="costCenter" error={errors.costCenter} required={false}>
        <Input
          id="costCenter"
          value={values.costCenter ?? ''}
          maxLength={255}
          onChange={(v) => set('costCenter', v)}
        />
      </FormField>
      <FormField label="Notes" htmlFor="notes" error={errors.notes} required={false}>
        <TextArea
          id="notes"
          value={values.notes ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('notes', v)}
        />
      </FormField>
      <FormField label="Effective From" htmlFor="effectiveFrom" error={errors.effectiveFrom} required={false}>
        <DateInput
          id="effectiveFrom"
          value={values.effectiveFrom ?? ''}
          onChange={(v) => set('effectiveFrom', v)}
        />
      </FormField>
      <FormField label="Effective To" htmlFor="effectiveTo" error={errors.effectiveTo} required={false}>
        <DateInput
          id="effectiveTo"
          value={values.effectiveTo ?? ''}
          onChange={(v) => set('effectiveTo', v)}
        />
      </FormField>
      <div className="entity-form__actions">
        {onCancel ? (
          <Button type="button" variant="ghost" onClick={onCancel}>
            Cancel
          </Button>
        ) : null}
        <Button type="submit" disabled={busy}>
          {busy ? 'Saving…' : submitLabel}
        </Button>
      </div>
    </form>
  );
}

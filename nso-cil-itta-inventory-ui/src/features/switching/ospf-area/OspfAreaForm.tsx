import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { OSPF_AREA_AREA_TYPE_OPTIONS, OSPF_AREA_CRITICALITY_OPTIONS, OspfAreaAreaType, OspfAreaCriticality } from './types';
import { validateOspfArea, cleanOspfAreaValues } from './schema';
import { OSPFAREA_DEFAULTS } from './types';
import type { OspfAreaFormValues, OspfAreaInput } from './types';

interface Props {
  initial?: OspfAreaFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: OspfAreaInput) => void;
  onCancel?: () => void;
}

export function OspfAreaForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<OspfAreaFormValues>({ ...OSPFAREA_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof OspfAreaFormValues>(key: K, value: OspfAreaFormValues[K]) {
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
    const result = validateOspfArea(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanOspfAreaValues(values as Record<string, unknown>) as unknown as OspfAreaInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Ospf Area form">
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
      <FormField label="Area Id" htmlFor="areaId" error={errors.areaId} required={true}>
        <Input
          id="areaId"
          value={values.areaId ?? ''}
          maxLength={255}
          onChange={(v) => set('areaId', v)}
        />
      </FormField>
      <FormField label="Area Type" htmlFor="areaType" error={errors.areaType} required={false}>
        <Select
          id="areaType"
          value={values.areaType ?? ''}
          options={OSPF_AREA_AREA_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('areaType', (v || undefined) as OspfAreaAreaType | undefined)}
        />
      </FormField>
      <FormField label="Device Count" htmlFor="deviceCount" error={errors.deviceCount} required={false}>
        <NumberInput
          id="deviceCount"
          value={values.deviceCount}
          step={1}
          onChange={(v) => set('deviceCount', v)}
        />
      </FormField>
      <FormField label="Auth Type" htmlFor="authType" error={errors.authType} required={false}>
        <Input
          id="authType"
          value={values.authType ?? ''}
          maxLength={255}
          onChange={(v) => set('authType', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={OSPF_AREA_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as OspfAreaCriticality | undefined)}
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

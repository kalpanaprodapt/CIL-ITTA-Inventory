import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { LINE_CARD_CRITICALITY_OPTIONS, LineCardCriticality } from './types';
import { validateLineCard, cleanLineCardValues } from './schema';
import { LINECARD_DEFAULTS } from './types';
import type { LineCardFormValues, LineCardInput } from './types';

interface Props {
  initial?: LineCardFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: LineCardInput) => void;
  onCancel?: () => void;
}

export function LineCardForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<LineCardFormValues>({ ...LINECARD_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof LineCardFormValues>(key: K, value: LineCardFormValues[K]) {
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
    const result = validateLineCard(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanLineCardValues(values as Record<string, unknown>) as unknown as LineCardInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Line Card form">
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
      <FormField label="Chassis Id" htmlFor="chassisId" error={errors.chassisId} required={true}>
        <Input
          id="chassisId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.chassisId ?? ''}
          onChange={(v) => set('chassisId', v)}
        />
      </FormField>
      <FormField label="Slot Number" htmlFor="slotNumber" error={errors.slotNumber} required={true}>
        <NumberInput
          id="slotNumber"
          value={values.slotNumber}
          step={1}
          onChange={(v) => set('slotNumber', v)}
        />
      </FormField>
      <FormField label="Card Type" htmlFor="cardType" error={errors.cardType} required={false}>
        <Input
          id="cardType"
          value={values.cardType ?? ''}
          maxLength={255}
          onChange={(v) => set('cardType', v)}
        />
      </FormField>
      <FormField label="Port Count" htmlFor="portCount" error={errors.portCount} required={false}>
        <NumberInput
          id="portCount"
          value={values.portCount}
          step={1}
          onChange={(v) => set('portCount', v)}
        />
      </FormField>
      <FormField label="Serial Number" htmlFor="serialNumber" error={errors.serialNumber} required={false}>
        <Input
          id="serialNumber"
          value={values.serialNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('serialNumber', v)}
        />
      </FormField>
      <FormField label="Firmware" htmlFor="firmware" error={errors.firmware} required={false}>
        <Input
          id="firmware"
          value={values.firmware ?? ''}
          maxLength={255}
          onChange={(v) => set('firmware', v)}
        />
      </FormField>
      <FormField label="Region" htmlFor="region" error={errors.region} required={false}>
        <Input
          id="region"
          value={values.region ?? ''}
          maxLength={255}
          onChange={(v) => set('region', v)}
        />
      </FormField>
      <FormField label="Owner" htmlFor="owner" error={errors.owner} required={false}>
        <Input
          id="owner"
          value={values.owner ?? ''}
          maxLength={255}
          onChange={(v) => set('owner', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={LINE_CARD_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as LineCardCriticality | undefined)}
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

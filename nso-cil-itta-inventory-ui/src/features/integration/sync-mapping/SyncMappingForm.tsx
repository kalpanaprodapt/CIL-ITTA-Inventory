import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';

import { validateSyncMapping, cleanSyncMappingValues } from './schema';
import { SYNCMAPPING_DEFAULTS } from './types';
import type { SyncMappingFormValues, SyncMappingInput } from './types';

interface Props {
  initial?: SyncMappingFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: SyncMappingInput) => void;
  onCancel?: () => void;
}

export function SyncMappingForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<SyncMappingFormValues>({ ...SYNCMAPPING_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof SyncMappingFormValues>(key: K, value: SyncMappingFormValues[K]) {
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
    const result = validateSyncMapping(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanSyncMappingValues(values as Record<string, unknown>) as unknown as SyncMappingInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Sync Mapping form">
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
      <FormField label="Job Id" htmlFor="jobId" error={errors.jobId} required={true}>
        <Input
          id="jobId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.jobId ?? ''}
          onChange={(v) => set('jobId', v)}
        />
      </FormField>
      <FormField label="Source Field" htmlFor="sourceField" error={errors.sourceField} required={true}>
        <Input
          id="sourceField"
          value={values.sourceField ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceField', v)}
        />
      </FormField>
      <FormField label="Target Field" htmlFor="targetField" error={errors.targetField} required={true}>
        <Input
          id="targetField"
          value={values.targetField ?? ''}
          maxLength={255}
          onChange={(v) => set('targetField', v)}
        />
      </FormField>
      <FormField label="Transform" htmlFor="transform" error={errors.transform} required={false}>
        <Input
          id="transform"
          value={values.transform ?? ''}
          maxLength={255}
          onChange={(v) => set('transform', v)}
        />
      </FormField>
      <FormField label="Required" htmlFor="required" error={errors.required} required={false}>
        <Checkbox
          id="required"
          checked={Boolean(values.required)}
          onChange={(v) => set('required', v)}
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
      <FormField label="Labels" htmlFor="labels" error={errors.labels} required={false}>
        <Input
          id="labels"
          value={values.labels ?? ''}
          maxLength={255}
          onChange={(v) => set('labels', v)}
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

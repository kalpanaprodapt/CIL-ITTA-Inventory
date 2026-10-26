import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { PATCH_SEVERITY_OPTIONS, PATCH_CRITICALITY_OPTIONS, PatchSeverity, PatchCriticality } from './types';
import { validatePatch, cleanPatchValues } from './schema';
import { PATCH_DEFAULTS } from './types';
import type { PatchFormValues, PatchInput } from './types';

interface Props {
  initial?: PatchFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: PatchInput) => void;
  onCancel?: () => void;
}

export function PatchForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<PatchFormValues>({ ...PATCH_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof PatchFormValues>(key: K, value: PatchFormValues[K]) {
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
    const result = validatePatch(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanPatchValues(values as Record<string, unknown>) as unknown as PatchInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Patch form">
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
      <FormField label="Version Id" htmlFor="versionId" error={errors.versionId} required={true}>
        <Input
          id="versionId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.versionId ?? ''}
          onChange={(v) => set('versionId', v)}
        />
      </FormField>
      <FormField label="Patch Id" htmlFor="patchId" error={errors.patchId} required={true}>
        <Input
          id="patchId"
          value={values.patchId ?? ''}
          maxLength={255}
          onChange={(v) => set('patchId', v)}
        />
      </FormField>
      <FormField label="Severity" htmlFor="severity" error={errors.severity} required={false}>
        <Select
          id="severity"
          value={values.severity ?? ''}
          options={PATCH_SEVERITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('severity', (v || undefined) as PatchSeverity | undefined)}
        />
      </FormField>
      <FormField label="Released On" htmlFor="releasedOn" error={errors.releasedOn} required={false}>
        <DateInput
          id="releasedOn"
          value={values.releasedOn ?? ''}
          onChange={(v) => set('releasedOn', v)}
        />
      </FormField>
      <FormField label="Reboot Required" htmlFor="rebootRequired" error={errors.rebootRequired} required={false}>
        <Checkbox
          id="rebootRequired"
          checked={Boolean(values.rebootRequired)}
          onChange={(v) => set('rebootRequired', v)}
        />
      </FormField>
      <FormField label="Criticality" htmlFor="criticality" error={errors.criticality} required={false}>
        <Select
          id="criticality"
          value={values.criticality ?? ''}
          options={PATCH_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as PatchCriticality | undefined)}
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

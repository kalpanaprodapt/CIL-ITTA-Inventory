import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { RECONCILIATION_RESULT_OUTCOME_OPTIONS, RECONCILIATION_RESULT_CRITICALITY_OPTIONS, ReconciliationResultOutcome, ReconciliationResultCriticality } from './types';
import { validateReconciliationResult, cleanReconciliationResultValues } from './schema';
import { RECONCILIATIONRESULT_DEFAULTS } from './types';
import type { ReconciliationResultFormValues, ReconciliationResultInput } from './types';

interface Props {
  initial?: ReconciliationResultFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ReconciliationResultInput) => void;
  onCancel?: () => void;
}

export function ReconciliationResultForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ReconciliationResultFormValues>({ ...RECONCILIATIONRESULT_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ReconciliationResultFormValues>(key: K, value: ReconciliationResultFormValues[K]) {
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
    const result = validateReconciliationResult(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanReconciliationResultValues(values as Record<string, unknown>) as unknown as ReconciliationResultInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Reconciliation Result form">
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
      <FormField label="Rule Id" htmlFor="ruleId" error={errors.ruleId} required={false}>
        <Input
          id="ruleId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.ruleId ?? ''}
          onChange={(v) => set('ruleId', v)}
        />
      </FormField>
      <FormField label="Outcome" htmlFor="outcome" error={errors.outcome} required={false}>
        <Select
          id="outcome"
          value={values.outcome ?? ''}
          options={RECONCILIATION_RESULT_OUTCOME_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('outcome', (v || undefined) as ReconciliationResultOutcome | undefined)}
        />
      </FormField>
      <FormField label="Diff Summary" htmlFor="diffSummary" error={errors.diffSummary} required={false}>
        <TextArea
          id="diffSummary"
          value={values.diffSummary ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('diffSummary', v)}
        />
      </FormField>
      <FormField label="Resolved By" htmlFor="resolvedBy" error={errors.resolvedBy} required={false}>
        <Input
          id="resolvedBy"
          value={values.resolvedBy ?? ''}
          maxLength={255}
          onChange={(v) => set('resolvedBy', v)}
        />
      </FormField>
      <FormField label="Source System" htmlFor="sourceSystem" error={errors.sourceSystem} required={false}>
        <Input
          id="sourceSystem"
          value={values.sourceSystem ?? ''}
          maxLength={255}
          onChange={(v) => set('sourceSystem', v)}
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
          options={RECONCILIATION_RESULT_CRITICALITY_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('criticality', (v || undefined) as ReconciliationResultCriticality | undefined)}
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

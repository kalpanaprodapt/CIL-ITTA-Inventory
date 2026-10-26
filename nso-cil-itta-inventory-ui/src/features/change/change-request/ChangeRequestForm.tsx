import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { CHANGE_REQUEST_CHANGE_TYPE_OPTIONS, CHANGE_REQUEST_RISK_LEVEL_OPTIONS, CHANGE_REQUEST_LIFECYCLE_STAGE_OPTIONS, ChangeRequestChangeType, ChangeRequestRiskLevel, ChangeRequestLifecycleStage } from './types';
import { validateChangeRequest, cleanChangeRequestValues } from './schema';
import { CHANGEREQUEST_DEFAULTS } from './types';
import type { ChangeRequestFormValues, ChangeRequestInput } from './types';

interface Props {
  initial?: ChangeRequestFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ChangeRequestInput) => void;
  onCancel?: () => void;
}

export function ChangeRequestForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ChangeRequestFormValues>({ ...CHANGEREQUEST_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ChangeRequestFormValues>(key: K, value: ChangeRequestFormValues[K]) {
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
    const result = validateChangeRequest(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanChangeRequestValues(values as Record<string, unknown>) as unknown as ChangeRequestInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Change Request form">
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
      <FormField label="Change Number" htmlFor="changeNumber" error={errors.changeNumber} required={true}>
        <Input
          id="changeNumber"
          value={values.changeNumber ?? ''}
          maxLength={255}
          onChange={(v) => set('changeNumber', v)}
        />
      </FormField>
      <FormField label="Change Type" htmlFor="changeType" error={errors.changeType} required={false}>
        <Select
          id="changeType"
          value={values.changeType ?? ''}
          options={CHANGE_REQUEST_CHANGE_TYPE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('changeType', (v || undefined) as ChangeRequestChangeType | undefined)}
        />
      </FormField>
      <FormField label="Risk Level" htmlFor="riskLevel" error={errors.riskLevel} required={false}>
        <Select
          id="riskLevel"
          value={values.riskLevel ?? ''}
          options={CHANGE_REQUEST_RISK_LEVEL_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('riskLevel', (v || undefined) as ChangeRequestRiskLevel | undefined)}
        />
      </FormField>
      <FormField label="Planned Start" htmlFor="plannedStart" error={errors.plannedStart} required={false}>
        <DateTimeInput
          id="plannedStart"
          value={values.plannedStart ?? ''}
          onChange={(v) => set('plannedStart', v)}
        />
      </FormField>
      <FormField label="Planned End" htmlFor="plannedEnd" error={errors.plannedEnd} required={false}>
        <DateTimeInput
          id="plannedEnd"
          value={values.plannedEnd ?? ''}
          onChange={(v) => set('plannedEnd', v)}
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
      <FormField label="Lifecycle Stage" htmlFor="lifecycleStage" error={errors.lifecycleStage} required={false}>
        <Select
          id="lifecycleStage"
          value={values.lifecycleStage ?? ''}
          options={CHANGE_REQUEST_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as ChangeRequestLifecycleStage | undefined)}
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

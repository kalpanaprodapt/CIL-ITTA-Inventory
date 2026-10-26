import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { AUDIT_RECORD_LIFECYCLE_STAGE_OPTIONS, AuditRecordLifecycleStage } from './types';
import { validateAuditRecord, cleanAuditRecordValues } from './schema';
import { AUDITRECORD_DEFAULTS } from './types';
import type { AuditRecordFormValues, AuditRecordInput } from './types';

interface Props {
  initial?: AuditRecordFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: AuditRecordInput) => void;
  onCancel?: () => void;
}

export function AuditRecordForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<AuditRecordFormValues>({ ...AUDITRECORD_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof AuditRecordFormValues>(key: K, value: AuditRecordFormValues[K]) {
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
    const result = validateAuditRecord(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanAuditRecordValues(values as Record<string, unknown>) as unknown as AuditRecordInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Audit Record form">
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
      <FormField label="Actor" htmlFor="actor" error={errors.actor} required={true}>
        <Input
          id="actor"
          value={values.actor ?? ''}
          maxLength={255}
          onChange={(v) => set('actor', v)}
        />
      </FormField>
      <FormField label="Action" htmlFor="action" error={errors.action} required={true}>
        <Input
          id="action"
          value={values.action ?? ''}
          maxLength={255}
          onChange={(v) => set('action', v)}
        />
      </FormField>
      <FormField label="Entity Ref" htmlFor="entityRef" error={errors.entityRef} required={true}>
        <Input
          id="entityRef"
          value={values.entityRef ?? ''}
          maxLength={255}
          onChange={(v) => set('entityRef', v)}
        />
      </FormField>
      <FormField label="Occurred At" htmlFor="occurredAt" error={errors.occurredAt} required={true}>
        <DateTimeInput
          id="occurredAt"
          value={values.occurredAt ?? ''}
          onChange={(v) => set('occurredAt', v)}
        />
      </FormField>
      <FormField label="Before State" htmlFor="beforeState" error={errors.beforeState} required={false}>
        <TextArea
          id="beforeState"
          value={values.beforeState ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('beforeState', v)}
        />
      </FormField>
      <FormField label="After State" htmlFor="afterState" error={errors.afterState} required={false}>
        <TextArea
          id="afterState"
          value={values.afterState ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('afterState', v)}
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
          options={AUDIT_RECORD_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as AuditRecordLifecycleStage | undefined)}
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

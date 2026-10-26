import { useState } from 'react';
import { Button } from '@/shared/components/Button';
import { Checkbox, DateInput, DateTimeInput, FormField, Input, NumberInput, Select, TextArea } from '@/shared/components/form';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import type { LifecycleStatus } from '@/shared/types';
import { COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_OPTIONS, ComplianceExceptionLifecycleStage } from './types';
import { validateComplianceException, cleanComplianceExceptionValues } from './schema';
import { COMPLIANCEEXCEPTION_DEFAULTS } from './types';
import type { ComplianceExceptionFormValues, ComplianceExceptionInput } from './types';

interface Props {
  initial?: ComplianceExceptionFormValues;
  submitLabel?: string;
  busy?: boolean;
  serverError?: string;
  onSubmit: (input: ComplianceExceptionInput) => void;
  onCancel?: () => void;
}

export function ComplianceExceptionForm({ initial, submitLabel = 'Save', busy = false, serverError, onSubmit, onCancel }: Props) {
  const [values, setValues] = useState<ComplianceExceptionFormValues>({ ...COMPLIANCEEXCEPTION_DEFAULTS, ...initial });
  const [errors, setErrors] = useState<Record<string, string>>({});

  function set<K extends keyof ComplianceExceptionFormValues>(key: K, value: ComplianceExceptionFormValues[K]) {
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
    const result = validateComplianceException(values as Record<string, unknown>);
    if (!result.ok) {
      setErrors(result.errors);
      return;
    }
    onSubmit(cleanComplianceExceptionValues(values as Record<string, unknown>) as unknown as ComplianceExceptionInput);
  }

  return (
    <form className="entity-form" onSubmit={submit} noValidate aria-label="Compliance Exception form">
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
      <FormField label="Rule Id" htmlFor="ruleId" error={errors.ruleId} required={true}>
        <Input
          id="ruleId"
          placeholder="00000000-0000-0000-0000-000000000000"
          value={values.ruleId ?? ''}
          onChange={(v) => set('ruleId', v)}
        />
      </FormField>
      <FormField label="Target Ref" htmlFor="targetRef" error={errors.targetRef} required={true}>
        <Input
          id="targetRef"
          value={values.targetRef ?? ''}
          maxLength={255}
          onChange={(v) => set('targetRef', v)}
        />
      </FormField>
      <FormField label="Justification" htmlFor="justification" error={errors.justification} required={true}>
        <TextArea
          id="justification"
          value={values.justification ?? ''}
          maxLength={4000}
          rows={4}
          onChange={(v) => set('justification', v)}
        />
      </FormField>
      <FormField label="Approved By" htmlFor="approvedBy" error={errors.approvedBy} required={false}>
        <Input
          id="approvedBy"
          value={values.approvedBy ?? ''}
          maxLength={255}
          onChange={(v) => set('approvedBy', v)}
        />
      </FormField>
      <FormField label="Expires On" htmlFor="expiresOn" error={errors.expiresOn} required={false}>
        <DateInput
          id="expiresOn"
          value={values.expiresOn ?? ''}
          onChange={(v) => set('expiresOn', v)}
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
          options={COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_OPTIONS}
          allowEmpty={true}
          onChange={(v) => set('lifecycleStage', (v || undefined) as ComplianceExceptionLifecycleStage | undefined)}
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
